import {OrganizationalUnit} from "@/api/api-organization.ts";
import {TreeDataItem} from "@/ui-components/ui/tree.tsx";

export function teamsToTree(unit: OrganizationalUnit[] | undefined): TreeDataItem[] {
    if (unit) {
        let tree: TreeDataItem[] = [];

        unit.forEach(team => {
            let children: TreeDataItem[] = [];
            if (team.units) {
                children.push(...teamsToTree(team.units));
            }
            if (team.members) {
                team.members.forEach(member => {
                    children.push({
                        id: member.id,
                        name: member.id,
                        type: 'member'
                    });
                })
            }
            tree.push({
                id: team.id,
                name: team.name,
                type: 'team',
                teams: children
            });
        });
        return tree;
    } else {
        return [];
    }
}
