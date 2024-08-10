import {Building, RefreshCw} from "lucide-react";
import {useQuery, useSuspenseQuery} from "@tanstack/react-query";
import {api} from "@/api/api.ts";
import {useState} from "react";

export function Header() {
    const {data: organizationInfo} = useSuspenseQuery(api.organization.infoQuery);
    const scheduleQuery = useQuery(api.synchronization.scheduleQuery);
    const [sync, setSync] = useState(false);

    function scheduleSync() {
        setSync(scheduleQuery.data?.pending ?? false);
    }

    return <div className="flex h-14 items-center border-b px-4 min-h-14 lg:px-6  justify-between w-full">
        <Building>
        </Building>
        {organizationInfo?.root.name}
        <RefreshCw className={sync && "animate-spin"} onClick={scheduleSync}></RefreshCw>
    </div>
}
