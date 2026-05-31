export interface Product {
  id?: number;
  productName: string;
  quantityPerUnit?: string;
  unitPrice: number;
  unitsInStock: number;
  unitsOnOrder?: number;
  reorderLevel?: number;
  discontinued?: number;
  categoryId: number;
  supplierId: number;
}

export interface Category {
  id?: number;
  categoryName: string;
  description?: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  username: string;
  email?: string;
  roles: string[];
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  password: string;
  email: string;
  fullName?: string;
}
