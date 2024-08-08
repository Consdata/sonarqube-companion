import {http} from "@/api/api.ts";

interface OrganizationInfo {
    name: string,
    organizationalUnits: OrganizationalUnit[]
}

export interface OrganizationalUnit {
    id: string,
    name: string,
    units: OrganizationalUnit[],
    members: User[]
}

export interface User {
    id: string
}

export class OrganizationApi {
    info = () => http.get<OrganizationInfo>("/organization/info").then(response => response.data);
    infoQuery = {queryKey: ["organizationInfo"], queryFn: this.info};

}

