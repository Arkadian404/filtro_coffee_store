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
