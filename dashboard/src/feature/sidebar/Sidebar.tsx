import {BookUser, Building, RefreshCw, Users} from "lucide-react";
import {Tree} from "@/ui-components/ui/tree.tsx";
import {Button} from "@/ui-components/ui/button.tsx";
import {useQuery} from "@tanstack/react-query";
import {api} from "@/api/api.ts";
import {useDashboardStore} from "@/feature/dashboard/dashboard-state.ts";


export function Sidebar() {

    const data = [
        {id: "1", name: "Unread"},
        {id: "2", name: "Threads"},
        {
            id: "3",
            name: "Chat Roasdsdasdaadsdasdsadsadsaoms",
            children: [
                {id: "c1", name: "General"},
                {id: "c2", name: "Random"},
                {id: "c3", name: "Open Source Projects"},
            ],
        },
        {
            id: "4",
            name: "Direct Messages",
            children: [
                {
                    id: "d1",
                    name: "Alice",
                    children: [
                        {id: "d11", name: "Alice2"},
                        {id: "d12", name: "Bob2"},
                        {id: "d13", name: "Charlie2"},
                    ],
                },
                {id: "d2", name: "Bob"},
                {id: "d3", name: "Charlie"},
            ],
        },
        {
            id: "5",
            name: "Direct Messages",
            children: [
                {
                    id: "e1",
                    name: "Alice",
                    children: [
                        {id: "e11", name: "Alice2"},
                        {id: "e12", name: "Bob2"},
                        {id: "e13", name: "Charlie2"},
                    ],
                },
                {id: "e2", name: "Bob"},
                {id: "e3", name: "Charlie"},
            ],
        },
        {
            id: "6",
            name: "Direct Messages",
            children: [
                {
                    id: "f1",
                    name: "Alice",
                    children: [
                        {id: "f11", name: "Alice2"},
                        {id: "f12", name: "Bob2"},
                        {id: "f13", name: "Charlie2"},
                    ],
                },
                {id: "f2", name: "Bob"},
                {id: "f3", name: "Charlie"},
            ],
        },
    ];

    const dashboardStore = useDashboardStore();

    const organizationInfoQuery = useQuery(api.organization.infoQuery)

    return (
        <div className="hidden border-r bg-muted/40 md:block">
            <div className="flex h-full max-h-screen flex-col gap-2">
                <div className="flex h-14 items-center border-b px-4 min-h-14 lg:px-6">
                    <Building>
                    </Building>
                    {organizationInfoQuery.data?.name}

                    <RefreshCw className="animate-spin"></RefreshCw>
                </div>
                <Tree
                    data={data}
                    className="h-full"
                    initialSlelectedItemId="f12"
                    onSelectChange={(item) => dashboardStore.selectTeam(item?.name ?? "")}
                    folderIcon={BookUser}
                    itemIcon={Users}
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
