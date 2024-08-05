import {http} from "@/api/api.ts";

interface SynchronizationStatus {
    pending: boolean
}

export class SynchronizationApi {
    status = () => http.get<SynchronizationStatus>("synchronization/status").then(response => response.data);
    statusQuery = {queryKey: ["syncStatus"], queryFn: this.status};

    schedule = () => http.get<SynchronizationStatus>("synchronization/schedule").then(response => response.data);
}

