export interface ServerConfig {
  id: string;
  uuid: string;
  url: string;
  authentication: Authentication;
  blacklistUsers: string[];
  aliases: string[];
}

export interface Authentication {
  type: string;
  params: { [key: string]: string }
}
