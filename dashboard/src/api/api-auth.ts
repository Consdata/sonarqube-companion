import axios from "axios";
import {httpConfig} from "@/api/api.ts";

export class AuthApi {
    login = (username: string, password: string) => axios.get("/auth/login", {
        ...httpConfig,
        auth: {username, password}
    });
}

