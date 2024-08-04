import axios from "axios";
import {useUserStore} from "@/feature/auth/auth-state.ts";
import {AuthApi} from "@/api/api-auth.ts";
import {OrganizationApi} from "@/api/api-organization.ts";
import {SynchronizationApi} from "@/api/api-synchronization.ts";

export const httpConfig = {
    baseURL: "/api",
    withCredentials: true,
    withXSRFToken: true,
    timeout: 6000,
    headers: {
        Accept: "application/json",
    },
};

export const http = axios.create(httpConfig);

http.interceptors.request.use((config) => {
    const user = useUserStore.getState().user;

    if (user) {
        config.auth = {
            username: user.username,
            password: user.password
        }
    }

    return config;
});

http.interceptors.response.use(
    undefined,
    (error) => {
        switch (error.response.status) {
            case 401:
                window.location.href = "/login"
                return Promise.reject(error);
            case 403:
                window.location.href = "/login"
                return Promise.reject(error);
            default:
                return Promise.reject(error);
        }
    }
);

export class Api {
    auth = new AuthApi();
    organization = new OrganizationApi();
    synchronization = new SynchronizationApi();
}

export const api = new Api();
