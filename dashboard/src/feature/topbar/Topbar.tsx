import {Button} from "@/ui-components/ui/button.tsx";
import {UserMenu} from "@/feature/user-menu/UserMenu.tsx";
import {useDashboardStore} from "@/feature/dashboard/dashboard-state.ts";


export function Topbar() {
    const dashboardStore = useDashboardStore();

    return (
        <header className="flex h-14 max-h-14 items-center gap-4 border-b bg-muted/40 px-4 lg:h-[60px] lg:px-6">
            <div className="w-full flex-1">
                <nav
                    className="hidden flex-col gap-6 text-lg font-medium md:flex md:flex-row md:items-center md:gap-5 md:text-sm lg:gap-6">
                    <div className="border-r px-4 flex items-center gap-4">
                        <div className="border-r gap-4 font-bold">{dashboardStore.selectedUnit}</div>
                    </div>
                    <Button
                        variant="ghost"
                        className="text-muted-foreground transition-colors hover:text-foreground"
                    >
                        Overview
                    </Button>
                </nav>
            </div>
            <UserMenu></UserMenu>
        </header>
    );
}
