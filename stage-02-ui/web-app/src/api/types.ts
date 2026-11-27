export interface ApiResponse<T> {
    code: number;
    message?: string;
    results?: T;
}

export interface PageResponse<T> {
    currentPage: number;
    totalPage: number;
    pageSize: number;
    totalElements: number;
    data: T[];
}

// User
export interface User {
    id: string;
    username: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    birthDate?: string;
    roles?: Role[];
}

export interface Role {
    name: string;
    description?: string;
}

export interface UserCreationRequest {
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    email: string;
    birthDate?: string | null;
}

// Authenticate
export interface AuthenticationResponse {
    token: string;
    authenticated: boolean;
}

// Product
export interface Phone {
    id: string;
    name: string;
    description: string;
    categories: Category[];
    options: PhoneOption[];
    detail: PhoneDetail;
    images: string[];
    createAt: string;
}

export interface PhoneOption {
    type: string;
    price: number;
    color: string;
}

export interface PhoneDetail {
    id: number;
    cpu: string;
    ram: string;
    storage: string;
    screenSize: string;
    battery: string;
    camera: string;
    operatingSystem: string;
}

export interface Category {
    name: string;
    description: string;
}

// Accessory
export interface Accessory {
    id: string;
    name: string;
    description: string;
    images: string[];
    categories: Category[];
    accessoryOptions: AccessoryOption[];
    createdAt: string;
}

export interface AccessoryOption {
    type: string;
    price: string;
}