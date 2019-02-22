export class AuthenticationData {
  type: string;
  params: TokenAuthenticationData | BasicAuthenticationData;

  constructor(data?: any) {
    if (data) {
      this.type = data.type;
      if (this.type === 'token') {
        this.params = new TokenAuthenticationData(data.params);
      }
      if (this.type === 'basic') {
        this.params = new BasicAuthenticationData(data.params);
      }
    } else {
      this.type = 'none';
    }
  }
}

export class TokenAuthenticationData {
  token: string;

  constructor(data: any) {
    this.token = data.token;
  }
}

export class BasicAuthenticationData {
  user: string;
  password: string;

  constructor(data: any) {
    this.user = data.user;
    this.password = data.password;
  }
}
