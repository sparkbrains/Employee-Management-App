package sparkbrains.em.features.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import sparkbrains.em.R
import sparkbrains.em.features.auth.login.viewmodel.LoginViewModel
import sparkbrains.em.navigation.routes.RootRoutes
import sparkbrains.em.network.utils.NetworkResult
import sparkbrains.em.ui.components.ButtonOutlinedFilled
import sparkbrains.em.ui.components.TextFieldOutlined

@Composable
fun LoginScreen(navController: NavController = rememberNavController()) {

    val viewmodel = koinViewModel<LoginViewModel>()

    val state = viewmodel.state.collectAsStateWithLifecycle().value
    val loginResponse = viewmodel.loginResponse.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    LaunchedEffect(loginResponse) {
        if (loginResponse is NetworkResult.Success) {
            navController.popBackStack(RootRoutes.AuthGraph, true)
            navController.navigate(RootRoutes.DashBoardGraph)
        }

        if (loginResponse is NetworkResult.Error) {
            Toast.makeText(
                context,
                "Error occurred: ${loginResponse.errorMessage}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .imePadding()
    ) {
        val (logoRef, emailRef, passwordRef, loginRef) = createRefs()

        Image(
            modifier = Modifier.constrainAs(logoRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            painter = painterResource(R.drawable.logo),
            contentDescription = stringResource(R.string.app_name)
        )

        TextFieldOutlined(
            modifier = Modifier.constrainAs(emailRef) {
                top.linkTo(logoRef.bottom, 12.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            supportingTextError = state.emailError,
            value = state.email,
            onValueChange = viewmodel::onEmailChange,
            label = stringResource(R.string.email),
            leadingIcon = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )

        TextFieldOutlined(
            modifier = Modifier.constrainAs(passwordRef) {
                top.linkTo(emailRef.bottom, 12.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            value = state.password,
            onValueChange = viewmodel::onPasswordChange,
            label = stringResource(R.string.password),
            leadingIcon = Icons.Default.Lock,
            keyboardType = KeyboardType.Password,
            isPassword = true,
            imeAction = ImeAction.Next,
            supportingTextError = state.passwordError,
        )

        ButtonOutlinedFilled(
            modifier = Modifier.constrainAs(loginRef) {
                top.linkTo(passwordRef.bottom, 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            isLoading = loginResponse is NetworkResult.Loading,
            text = R.string.login,
            onClick = {
                viewmodel.validateAndSubmit()
            }
        )
    }
}