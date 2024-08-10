import {Users} from "lucide-react";
import {Button} from "@/ui-components/ui/button.tsx";
import {Suspense} from "react";
import {OrganizationTree} from "@/feature/sidebar/organization-tree/organization-tree.tsx";
import {OrganizationTreeSkeleton} from "@/feature/sidebar/organization-tree/organization-tree-skeleton.tsx";
import {HeaderSkeleton} from "@/feature/sidebar/header/header-skeleton.tsx";
import {Header} from "@/feature/sidebar/header/header.tsx";

export function Sidebar() {
    return (
        <div className="hidden border-r bg-muted/40 md:block">
            <div className="flex h-full max-h-screen flex-col gap-2">
                <Suspense fallback={<HeaderSkeleton/>}>
                    <Header></Header>
                </Suspense>
                <Suspense fallback={<OrganizationTreeSkeleton/>}>
                    <OrganizationTree></OrganizationTree>
                </Suspense>
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
