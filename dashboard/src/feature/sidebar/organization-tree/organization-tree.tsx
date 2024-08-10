import {useSuspenseQuery} from "@tanstack/react-query";
import {api} from "@/api/api.ts";
import {useDashboardStore} from "@/feature/dashboard/dashboard-state.ts";
import {teamsToTree} from "@/feature/sidebar/teamsToTree.ts";
import {Users} from "lucide-react";
import {Tree} from "@/ui-components/ui/tree.tsx";

export function OrganizationTree() {
    const {data: organizationInfo} = useSuspenseQuery(api.organization.infoQuery);
    const dashboardStore = useDashboardStore();
    const {data: structure} = useSuspenseQuery({
        queryKey: ["organizationTeamTree"],
        queryFn: () => organizationInfo ? teamsToTree(organizationInfo.root.units) : [],
    });

    return <Tree
        data={structure}
        className="h-full"
        initialSlelectedItemId="f12"
        onSelectChange={(item) => dashboardStore.selectTeam(item?.name ?? "")}
        folderIcon={Users}
        itemIcon={Users}
    />;

}
