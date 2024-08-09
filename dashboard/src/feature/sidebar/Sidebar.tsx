import {Building, RefreshCw, User, Users} from "lucide-react";
import {Tree} from "@/ui-components/ui/tree.tsx";
import {Button} from "@/ui-components/ui/button.tsx";
import {useQuery} from "@tanstack/react-query";
import {api} from "@/api/api.ts";
import {useDashboardStore} from "@/feature/dashboard/dashboard-state.ts";
import {teamsToTree} from "@/feature/sidebar/teamsToTree.ts";
import {useState} from "react";

export function Sidebar() {

    const organizationInfoQuery = useQuery(api.organization.infoQuery);
    const scheduleQuery = useQuery(api.synchronization.scheduleQuery);
    const dashboardStore = useDashboardStore();
    const [sync, setSync] = useState(false);

    const teamsTreeQuery = useQuery({
        queryKey: ["organizationTeamTree"],
        queryFn: () => teamsToTree(organizationInfoQuery.data?.root.units),
        enabled: !!organizationInfoQuery.data
    });

    function scheduleSync() {
        setSync(scheduleQuery.data?.pending ?? false);
    }

    return (
        <div className="hidden border-r bg-muted/40 md:block">
            <div className="flex h-full max-h-screen flex-col gap-2">
                <div className="flex h-14 items-center border-b px-4 min-h-14 lg:px-6  justify-between w-full">
                    <Building>
                    </Building>
                    {organizationInfoQuery.data?.root.name}
                    <RefreshCw className={sync && "animate-spin"} onClick={scheduleSync}></RefreshCw>
                </div>
                <Tree
                    data={teamsTreeQuery.data ?? []}
                    className="h-full"
                    initialSlelectedItemId="f12"
                    onSelectChange={(item) => dashboardStore.selectTeam(item?.name ?? "")}
                    folderIcon={Users}
                    itemIcon={User}
                />
                <div className="flex p-4 border-t h-20">
                    <Button
                        variant="ghost"
                        className="flex items-center gap-3 text-muted-foreground transition-all hover:text-primary"
                    >
                        <Users className="h-4 w-4"/>
                        Projects overview
                    </Button>
                </div>
            </div>
        </div>
    );
}
