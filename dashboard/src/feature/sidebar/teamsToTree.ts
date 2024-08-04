import {Team} from "@/api/api-organization.ts";
import {TreeDataItem} from "@/ui-components/ui/tree.tsx";

export function teamsToTree(teams: Team[] | undefined): TreeDataItem[] {
    if (teams) {
        let tree: TreeDataItem[] = [];

        teams.forEach(team => {
            let children: TreeDataItem[] = [];
            if (team.teams) {
                children.push(...teamsToTree(team.teams));
            }
            if (team.members) {
                team.members.forEach(member => {
                    children.push({
                        id: member.id,
                        name: member.id
                    });
                })
            }
            tree.push({
                id: team.id,
                name: team.name,
                teams: children
            });
        });
        return tree;
    } else {
        return [];
    }
}
