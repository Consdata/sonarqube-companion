export class WebhookDefinition {
  uuid: string;
  name: string;
  action: WebhookAction;
  trigger: WebhookTrigger;
  callbacks: WebhookCallback[];

  constructor(data: any) {
    this.uuid = data.uuid;
    this.name = data.name;
    if (data.action) {
      if (data.action.type == 'NO_IMPROVEMENT') {
        this.action = new NoImprovementWebhookAction(data.action);
      }
    }

    if (data.trigger) {
      if (data.trigger.type == 'CRON') {
        this.trigger = new CronWebhookTrigger(data.trigger);
      }
      if (data.trigger.type == 'REST') {
        this.trigger = new RestWebhookTrigger(data.trigger);
      }
    }

    if (data.callbacks) {
      this.callbacks = data.callbacks.map(c => {
        if (c.type == 'JSON') {
          return new JSONWebhookCallback(c);
        }
        if (c.type == 'POST') {
          return new PostWebhookCallback(c);
        }
      })
    }
  }
}

export class WebhookAction {
  type: string;

  constructor(data: any) {
    this.type = data.type;
  }
}

export class NoImprovementWebhookAction extends WebhookAction {
  period: string;
  severity: string[];

  constructor(data: any) {
    super(data);
    this.period = data.period;
    this.severity = data.severity;
  }
}

export class WebhookTrigger {
  type: string;

  constructor(data: any) {
    this.type = data.type;
  }

}

export class CronWebhookTrigger extends WebhookTrigger {
  definition: string;

  constructor(data: any) {
    super(data);
    this.definition = data.definition;
  }
}

export class RestWebhookTrigger extends WebhookTrigger {
  method: string;
  endpoint: string;

  constructor(data: any) {
    super(data);
    this.method = data.method;
    this.endpoint = data.endpoint;
  }
}


export class WebhookCallback {
  type: string;
  name: string;
  uuid: string;

  constructor(data: any) {
    this.type = data.type;
    this.name = data.name;
    this.uuid = data.id;
  }
}

export class JSONWebhookCallback extends WebhookCallback {
  body: { [key: string]: any };

  constructor(data: any) {
    super(data);
    this.body = data.body;
  }
}

export class PostWebhookCallback extends WebhookCallback {
  body: { [key: string]: any };
  url: string;

  constructor(data: any) {
    super(data);
    this.body = data.body;
    this.url = data.url;
    console.log(this)
  }
}
