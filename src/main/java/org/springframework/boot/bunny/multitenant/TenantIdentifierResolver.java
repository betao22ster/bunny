package org.springframework.boot.bunny.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import static org.springframework.boot.bunny.multitenant.MultiTenantConstants.*;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	/**
	 * 
	 * AQUI ELE IDENTIFICA QUAL O BANCO QUE FOI CHAMADO. CASO NAO TENHA NENHUM IDENTIFICADO, 
	 * ELE USA O DEFAULT
	 * 
	 * 
	 */
	@Override
	public String resolveCurrentTenantIdentifier() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) {
			String tenantId = (String) requestAttributes.getAttribute(CURRENT_TENANT_IDENTIFIER, RequestAttributes.SCOPE_REQUEST);
			if (tenantId != null) {
				return tenantId;
			}
		}
		return DEFAULT_TENANT_ID;
	}

	public void forceCurrentTenantIndetifier(String tenant) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) {
			requestAttributes.setAttribute(CURRENT_TENANT_IDENTIFIER, tenant, RequestAttributes.SCOPE_REQUEST);
		}
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}
