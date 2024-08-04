import {http} from "@/api/api.ts";

interface OrganizationInfo {
    name: string,
    teams: Team[]
}

export interface Team {
    id: string,
    name: string,
    teams: Team[],
}

export interface TeamMember {
    id: string
}

export class OrganizationApi {
    info = () => http.get<OrganizationInfo>("/organization/info").then(response => response.data);
    infoQuery = {queryKey: ["organizationInfo"], queryFn: this.info};

}

