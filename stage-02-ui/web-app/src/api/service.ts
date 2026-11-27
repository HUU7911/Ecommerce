import { api, unwrap} from "./client.ts";
import type {
    User,
    UserCreationRequest,
    AuthenticationResponse
} from './types.ts'

// Identity-service
export const authApi = {
    login: (username: string, password: string) =>
        unwrap<AuthenticationResponse>(api.post('/auth/login', {
            username,
            password,
        })),
    logout: (token: string) =>
        api.post('/auth/logout', {
            token
        }),
    googleExchange: (code: string) =>
        unwrap<AuthenticationResponse>(api.post('/auth/outbound/identity', null, {
            params: {
                code
            }
        })),
    register: (body: UserCreationRequest) =>
        unwrap<User>(api.post("/users/create", body)),
};
