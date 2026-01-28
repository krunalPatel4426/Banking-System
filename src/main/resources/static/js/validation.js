$(document).ready(function(){
   $("#password").focus(function(){
       $('#password-validation').slideDown();
   });

   $('#password').blur(function(){
       $('#password-validation').slideUp();
   });

   $('#password').on('input',function(){
       var password = $(this).val();

       var rules = {
           'length': password.length >=8,
           'uppercase': /[A-Z]/.test(password),
           'number': /[0-9]/.test(password),
           'special': /[!@#$%^&*()*+?>.,<]/.test(password),
       }

       updateRule("#rule-length", rules.length);
       updateRule("#rule-uppercase", rules.uppercase);
       updateRule("#rule-number", rules.number);
       updateRule("#rule-special", rules.special);

       toggleSubmitButton(rules.length && rules.number && rules.special && rules.uppercase);
   });

   function updateRule(elementId, isValid){
        var element = $(elementId);
        var icon = element.find("i");

        if(isValid){
            element.addClass("valid").removeClass("invalid");
            icon.removeClass("fa-circle").addClass("fa-check-circle");
        }else{
            element.addClass("invalid").removeClass("valid");
            icon.removeClass("fa-check-circle").addClass("fa-circle");
        }
   }

   $("#username").on("blur", function(){

       var username = $(this).val();
       var feedback = $('#username-feedback');
       var input = $(this);

       $.ajax({
           url: '/api/check-username',
           type: 'GET',
           data: {username: username},
           success: function(exists){
               if(exists){
                   input.addClass("is-invalid").removeClass("is-valid");
                   feedback.text("Username already exists!").addClass('text-danger').removeClass('text-success');
                   $("#submitBtn").prop('disabled', true);
               }else{
                   input.addClass('is-valid').removeClass("is-invalid");
                   feedback.text("username is available.").addClass('text-success').removeClass('text-danger');
                   $("#submitBtn").prop('disabled', false);
               }
           },
           error: function(){
               console.log("error checking username.");
           }
       })
   })

   $('#email').on('blur', function(){
       var email = $(this).val();
       var feedback = $('#email-feedback');
       var input = $(this);

       $.ajax({
           url: '/api/check-email',
           type: 'GET',
           data: {email: email},
           success: function(exists){
               if(exists){
                   input.addClass("is-invalid").removeClass("is-valid");
                   feedback.text("Email already registered!").addClass('text-danger').removeClass('text-success');
                   $("#submitBtn").prop('disabled', true);
               }else{
                   input.addClass('is-valid').removeClass("is-invalid");
                   feedback.text("Email is available.").addClass('text-success').removeClass('text-danger');
                   $("#submitBtn").prop('disabled', false);
               }
           },
           error: function(){
               console.log("Error Checking email.");
           }
       });
   });

   function toggleSubmitButton(isValidPassword){
       if(isValidPassword && !$('#email').hasClass("is-invalid")){
           $('#submitBtn').prop('disabled', false);
       }else{
           $('#submitBtn').prop('disabled', true);
       }
   }

   $('#togglePassword').click(function(){
       var input = $('#password');
       var icon = $(this).find('i');
       if(input.attr('type') === 'password'){
           input.attr('type', 'text');
           icon.removeClass("fa-eye").addClass("fa-eye-slash");
       }else{
           input.attr('type', 'password');
           icon.removeClass("fa-eye-slash").addClass("fa-eye");
       }
   })
});