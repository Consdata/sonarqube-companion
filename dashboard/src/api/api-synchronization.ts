import {http} from "@/api/api.ts";

interface SynchronizationStatus {
    pending: boolean
}

export class SynchronizationApi {
    schedule = () => http.get<void>("/synchronization/schedule");

    status = () => http.get<SynchronizationStatus>("synchronization/status").then(response => response.data);
    statusQuery = {queryKey: ["syncStatus"], queryFn: this.status};

}

