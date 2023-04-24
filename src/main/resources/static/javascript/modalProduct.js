$(document).ready(function () {
  $("table .delete").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $("#deleteProductModal #deleteProductId").val(id);
  });

  $("table .edit").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $.ajax({
      type: "GET",
      url: `/api/product/find/${id}`,
      success: function (product) {
        $("#editProductModal #saveProductId").val(id);
        $("#editProductModal #productName").val(product.productName);
        $("#editProductModal #price").val(product.price);
        $("#editProductModal #quantity").val(product.quantity);
        $("#editProductModal #sold").val(product.sold);
        $("#editProductModal #description").val(product.description);
        $("#editFile").change(function () {
          let file = this.files[0];
          const reader = new FileReader();
          reader.onload = function (e) {
            $("#editProductModal #editFile").text(this[0].files[0].name);
          };
          reader.readAsDataURL(file);
        });
        $("#editProductModal #editStatus").val(product.status);
        $("#editProductModal #discount").val(product.discount);
        $("#editProductModal #editFlavor").val(product.flavor.id);
        $("#editProductModal #editCategory").val(product.category.id);
      },
    });
  });
});
