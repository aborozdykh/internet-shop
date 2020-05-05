<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <title>Registration</title>
</head>
<body>
<div class="container d-flex h-100">
    <div class="row align-self-center w-100">
        <div class="col-4 mx-auto">
            <h1 class="display-6" align="center">User registration</h1>
            <div class="jumbotron">
                <form action="${pageContext.request.contextPath}/registration" method="post">
                    <div class="form-group">
                        <input type="text" name="name" class="form-control" placeholder="Name"
                               value="${name}">
                    </div>
                    <div class="form-group">
                        <input type="text" name="login" class="form-control" placeholder="Login"
                               value="${login}">
                    </div>
                    <div class="form-group">
                        <input type="password" name="pwd" required class="form-control" placeholder="Password">
                    </div>
                    <div class="form-group">
                        <input type="password" name="pwd-repeat" required class="form-control"
                               placeholder="Repeat password">
                    </div>
                    <h6 style="color: red">${messageDifferentPassword}</h6>
                    <button type="submit" class="btn btn-primary btn-block">Register</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
