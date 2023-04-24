const entries = document.querySelector("#entries");

if (sessionStorage.getItem("sortType")) {
  entries.value = sessionStorage.getItem("sortType");
}
entries.addEventListener("change", (evt) => {
  const sortType = entries.value;
  const url = window.location.href.split("?")[0]; //loalhost:3030
  const queryUrl = `${url}?sortType=${sortType}`;
  window.location.href = queryUrl.toString();
  sessionStorage.setItem("sortType", sortType);
});
