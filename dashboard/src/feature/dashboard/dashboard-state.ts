import {create, StateCreator} from "zustand";
import {devtools, persist} from "zustand/middleware";


interface DashboardState {
    selectedUnit: string | null;

    selectUnit: (id: string) => void;
    clearSelectedUnit: () => void;
}

const dashboardStateSlice: StateCreator<DashboardState> = (set) => ({
    selectedUnit: null,
    selectUnit: (id: string) => set({selectedUnit: id}),
    clearSelectedUnit: () => set({selectedUnit: null})
});

const persistedDashboardStateStore = persist<DashboardState>(dashboardStateSlice, {
    name: 'dashboard'
});

export const useDashboardStore = create(
    devtools(
        persistedDashboardStateStore
    )
);
