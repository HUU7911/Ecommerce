import axios, {AxiosError, AxiosRequestConfig} from "axios";
import type { ApiResponse } from "./types.ts";

const API_URL = import.meta.env.VITE_API_URL;

const TOKEN_KEY= 'ecommerce';
export const AUTH_LOGOUT_EVENT = 'ecommerce:auth-logout';
export const AUTH_TOKEN_EVENT = 'ecommerce:auth-token';

export const tokenStore = {
    get: () => sessionStorage.getItem(TOKEN_KEY),
    set: (token: string) => sessionStorage.setItem(TOKEN_KEY, token),
    clear: () => sessionStorage.clear(),
};

export const api = axios.create({
    baseURL: API_URL
});

api.interceptors.request.use((config) => {
    const token = tokenStore.get();
    if (token) {
        config.headers.set('Authorization', `Bearer ${token}`);
    }
    return config;
});

// Auto refresh token
let refreshing: Promise<string> | null = null;

function refreshToken(): Promise<string> {
    if (!refreshing) {
        const current = tokenStore.get();
        refreshing = axios
            .post<ApiResponse<{
                token: string
            }>>(`${API_URL}/auth/refresh`, {
                token: current
            }).then((res) => {
                const token = res.data.results?.token;
                if (!token) throw new Error('No token');
                tokenStore.set(token);
                window.dispatchEvent(new CustomEvent(AUTH_TOKEN_EVENT, {
                    detail: token
                }));
                return token;
            }).finally(() => {
                refreshing = null;
            });
    }
    return refreshing;
}

type RetriableConfig = AxiosRequestConfig & { _retried?: boolean };

api.interceptors.response.use(
    (res) => res,
    async (error: AxiosError) => {
        const original = error.config as RetriableConfig | undefined;
        const hadToken = !!tokenStore.get();
        const isAuthCall = original?.url?.includes("/auth/");

        if (error.response?.status === 401 && original && hadToken && !original._retried && !isAuthCall) {
            original._retried = true;
            try {
                const token = await refreshToken();
                original.headers = {
                    ...(original.headers ?? {}),
                    Authorization: `Bearer ${token}`
                } as never;
            } catch {
                tokenStore.clear();
                window.dispatchEvent(new Event(AUTH_LOGOUT_EVENT));
            }
        }
        return Promise.reject(error);
    },
);

// Call api and get response from ApiReponse
export async function unwrap<T>(
    request: Promise<{
        data: ApiResponse<T>
    }>): Promise<T> {
    const res = await request;
    return res.data.results as T;
}

// Response Error from backend
const MESSAGE_VI: Record<string, string> = {
    Unauthenticated: 'Phiên đăng nhập không hợp lệ hoặc đã hết hạn.',
    unauthenticated: 'Phiên đăng nhập không hợp lệ hoặc đã hết hạn.',
    'Access denied': 'Bạn không có quyền thực hiện thao tác này.',
    'access denied': 'Bạn không có quyền thực hiện thao tác này.',
    'User not found': 'Không tìm thấy tài khoản.',
    'Invalid OTP': 'Mã OTP không đúng.',
    'OTP expired': 'Mã OTP đã hết hạn, hãy yêu cầu mã mới.',
    'Invalid reset token': 'Phiên đặt lại mật khẩu không hợp lệ hoặc đã hết hạn.',
    'search unavailable': 'Dịch vụ tìm kiếm đang không khả dụng.',
};

export function getErrorMessage(error: unknown, fallback = 'Đã có lỗi xảy ra, vui lòng thử lại.'): string {
    if (axios.isAxiosError(error)) {
        const data = error.response?.data as { message?: string; code?: number } | undefined;
        if (data?.message)
            return MESSAGE_VI[data.message] ?? data.message;
        if (error.response?.status === 401)
            return 'Phiên đăng nhập không hợp lệ hoặc đã hết hạn.';
        if (error.response?.status === 403)
            return 'Bạn không có quyền thực hiện thao tác này.';
        if (!error.response)
            return 'Không kết nối được tới máy chủ. Hãy kiểm tra API Gateway (cổng 8888).';
    }
    return fallback;
}

export function getErrorStatus(error: unknown): number | undefined {
    return axios.isAxiosError(error)
        ? error.response?.status
        : undefined;
}

export function getErrorCode(error: unknown): number | undefined {
    if (axios.isAxiosError(error))
        return (error.response?.data as {
            code?: number
        } | undefined)?.code;
    return undefined;
}