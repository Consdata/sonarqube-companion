export interface Event {
  id: number;
  groupId: string;
  projectId: string;
  userId: string;
  name: string;
  dateString: string;
  type: string;
  description: string;
  data: string;
  global: boolean;
  log: string;
  showOnTimeline: boolean;
}
