$(document).ready(function () {
  $("table .delete").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $("#deleteFlavorModal #deleteFlavorId").val(id);
  });

  $("table .edit").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $.ajax({
      type: "GET",
      url: `/api/flavor/find/${id}`,
      success: function (flavor) {
        $("#editFlavorModal #saveFlavorId").val(id);
        $("#editFlavorModal #flavorName").val(flavor.flavorName);
        $("#editFlavorModal #description").val(flavor.description);
        $("#editFlavorModal #editStatus").val(flavor.status);
      },
    });
  });
});
