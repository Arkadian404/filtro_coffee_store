$(document).ready(function () {
  $("table .delete").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $("#deleteCategoryModal #deleteCategoryId").val(id);
  });

  $("table .edit").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $.ajax({
      type: "GET",
      url: `/api/category/find/${id}`,
      success: function (category) {
        $("#editCategoryModal #saveCategoryId").val(id);
        $("#editCategoryModal #categoryName").val(category.categoryName);
        $("#editCategoryModal #editStatus").val(category.status);
      },
    });
  });
});
