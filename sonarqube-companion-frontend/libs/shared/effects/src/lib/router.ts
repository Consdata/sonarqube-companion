import {SerializedRouterStateSnapshot} from '@ngrx/router-store';


export function routeParams(snapshot: SerializedRouterStateSnapshot) {
  let route = snapshot.root;
  const params = new Map(Object.keys(route.params).map(key => [key, route.params[key]]));
  while (route.firstChild) {
    route = route.firstChild;
    Object.keys(route.params).forEach(key => params.set(key, route.params[key]));
  }
  return params;
}

export function queryParams(snapshot: SerializedRouterStateSnapshot) {
  let route = snapshot.root;
  const params = new Map(Object.keys(route.queryParams).map(key => [key, route.queryParams[key]]));
  while (route.firstChild) {
    route = route.firstChild;
    Object.keys(route.queryParams).forEach(key => params.set(key, route.queryParams[key]));
  }
  return params;
}
