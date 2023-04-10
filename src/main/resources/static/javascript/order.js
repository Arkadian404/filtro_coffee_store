// Example starter JavaScript for disabling form submissions if there are invalid fields
// (function () {
//   "use strict";
//
//   window.addEventListener(
//     "load",
//     function () {
//       // Fetch all the forms we want to apply custom Bootstrap validation styles to
//       var forms = document.getElementsByClassName("needs-validation");
//
//       // Loop over them and prevent submission
//       Array.prototype.filter.call(forms, function (form) {
//         form.addEventListener(
//           "submit",
//           function (event) {
//             if (form.checkValidity() === false) {
//               event.preventDefault();
//               event.stopPropagation();
//             }
//             form.classList.add("was-validated");
//           },
//           false
//         );
//       });
//     },
//     false
//   );
// })();

document.addEventListener("DOMContentLoaded", function (event) {
  const codRadio = document.querySelector("#codRadio");
  const creditRadio = document.querySelector("#creditRadio");
  const ccFields = document.querySelector("#cc-fields");

  function toggleCreditCardFields() {
    if (creditRadio.checked) {
      ccFields.classList.remove("d-none");
    } else {
      ccFields.classList.add("d-none");
    }
  }

  toggleCreditCardFields(); // Call the function once to initialize the state based on the default checked radio button

  codRadio.addEventListener("change", toggleCreditCardFields);
  creditRadio.addEventListener("change", toggleCreditCardFields);
});
