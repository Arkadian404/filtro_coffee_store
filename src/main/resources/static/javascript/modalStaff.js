$(document).ready(function () {
  $("table .delete").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $("#deleteStaffModal #deleteStaffId").val(id);
  });

  $("table .edit").on("click", function () {
    const id = $(this).parent().find("#id").val();
    $.ajax({
      type: "GET",
      url: `/api/staff/find/${id}`,
      success: function (staff) {
        $("#editStaffModal #saveStaffId").val(id);
        $("#editStaffModal #staffName").val(staff.name);
        $("#editStaffModal #dob").val(staff.dob);
        $("#editStaffModal #sex").val(staff.sex);
        $("#editStaffModal #phoneNumber").val(staff.phoneNumber);
        $("#editStaffModal #editStatus").val(staff.status);
        $("#editStaffModal #editAccount").val(staff.account.id);
      },
    });
  });
});
