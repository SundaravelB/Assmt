const app = document.getElementById('root');

const container = document.createElement('div');
container.setAttribute('class', 'container');

app.appendChild(container);

var request = new XMLHttpRequest();

try {
  request.open('GET', 'http://localhost:5050', true);
  request.onload = function () {
    var data = this.response;
    if (request.status >= 200 && request.status < 400) {

      const h1 = document.createElement('h1');
      h1.textContent = data;

      app.appendChild(h1);
    } else {
      const errorMessage = document.createElement('marquee');
      errorMessage.textContent = `Exception while invoking Web API`;
      app.appendChild(errorMessage);
    }
  }
  request.send();
}
catch (e) {
  const errorMessage = document.createElement('marquee');
  errorMessage.textContent = `Exception while invoking Web API`;
  app.appendChild(errorMessage);
}
