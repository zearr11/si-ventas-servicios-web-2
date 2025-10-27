package com.servicios.web2.ec1.services.interfaces.auth;

import com.servicios.web2.ec1.models.Usuario;
import com.servicios.web2.ec1.utils.dtos.request.AuthRequest;
import com.servicios.web2.ec1.utils.dtos.response.AuthResponse;

public interface IAuthService {

    AuthResponse login(AuthRequest request);
    Usuario obtenerUsuarioAutenticado();
}
