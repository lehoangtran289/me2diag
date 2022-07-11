export function debounce(func, wait) {
  let timeout;

  return function() {
    const context = this,
      args = arguments;

    const executeFunction = function () {
      func.apply(context, args);
    };

    clearTimeout(timeout);
    timeout = setTimeout(executeFunction, wait);
  };
}