import {create, StateCreator} from "zustand";
import {devtools, persist} from "zustand/middleware";


interface DashboardState {
    selectedTeam: string | null;

    selectTeam: (id: string) => void;
    clearSelectedTeam: () => void;
}

const dashboardStateSlice: StateCreator<DashboardState> = (set) => ({
    selectedTeam: null,
    selectTeam: (id: string) => set({selectedTeam: id}),
    clearSelectedTeam: () => set({selectedTeam: null})
});

const persistedDashboardStateStore = persist<DashboardState>(dashboardStateSlice, {
    name: 'dashboard'
});

export const useDashboardStore = create(
    devtools(
        persistedDashboardStateStore
    )
);
