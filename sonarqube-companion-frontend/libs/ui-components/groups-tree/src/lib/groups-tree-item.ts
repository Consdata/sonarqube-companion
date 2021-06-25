export interface GroupsTreeItem {
  name: string;
  uuid: string;
  groups: GroupsTreeItem[];
  healthStatus: string;
}
