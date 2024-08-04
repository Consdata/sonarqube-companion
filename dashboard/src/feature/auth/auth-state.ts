import {create, StateCreator} from "zustand";
import {devtools, persist} from "zustand/middleware";

interface User {
    username: string,
    password: string,
}

interface AuthState {
    user: User | null;
    setCredentials: (user: User) => void;
    removeCredentials: () => void;
}

const authStateSlice: StateCreator<AuthState> = (set) => ({
    user: null,
    setCredentials: (user) => set({user}),
    removeCredentials: () => set({user: null})
});

const persistedAuthStateStore = persist<AuthState>(authStateSlice, {
    name: "user",
});

export const useUserStore = create(
    devtools(
        persistedAuthStateStore
    )
);
