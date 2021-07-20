module.exports = {
  packages: {
    '@swimlane/ngx-charts': {
      entryPoints: {
        '.': { override: { main: undefined } },
      }
    }
  }
};
// TODO tmp workaround remove after fix for https://github.com/swimlane/ngx-charts/issues/1636
