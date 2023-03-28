$(document).ready(function () {
  $("#file").change(function () {
    showImage(this);
  });
});

function showImage(fileInput) {
  let file = fileInput.files[0];
  let reader = new FileReader();

  reader.onload = function (e) {
    $("#uploaded").attr("src", e.target.result);
  };

  reader.readAsDataURL(file);
}

const sortSelect = document.getElementById("sortSelect");
if (sessionStorage.getItem("sortType")) {
  sortSelect.value = sessionStorage.getItem("sortType");
}
sortSelect.addEventListener("change", (e) => {
  const sortType = sortSelect.value;
  const url = window.location.href.split("?")[0];
  let queryUrl;
  if (url.includes("/search")) {
    const params = new URLSearchParams(window.location.search);
    if (params.has("sortType")) {
      params.set("sortType", sortType);
    } else {
      params.append("sortType", sortType);
    }
    queryUrl = `${url}?${params.toString()}`;
  } else {
    queryUrl = `${url}?sortType=${sortType}`;
  }
  window.location.href = queryUrl.toString();
  sessionStorage.setItem("sortType", sortType);
  console.log(queryUrl, url);
});
