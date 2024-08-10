import {Building, RefreshCw} from "lucide-react";
import {Skeleton} from "@/ui-components/ui/skeleton.tsx";

export function HeaderSkeleton() {
    return <>
        <div className="flex h-14 items-center border-b px-4 min-h-14 lg:px-6  justify-between w-full">
            <Building>
            </Building>
            <Skeleton className="h-4 w-full ml-4 mr-4"/>
            <RefreshCw></RefreshCw>
        </div>
    </>
}
