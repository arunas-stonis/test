import { PartnerComponent } from './partner.component';
import { JhiLanguageService } from '../../shared';

export const partnerState = {
    name: 'partner',
    parent: 'entity',
    url: '/partner',
    data: {
        authorities: ['ROLE_USER', 'ROLE_ADMIN'],
        pageTitle: 'global.menu.entity.partner'
    },
    views: {
        'content@': {
            component: PartnerComponent
        }
    },
    resolve: [{
        token: 'translate',
        deps: [JhiLanguageService],
        resolveFn: (languageService) => languageService.setLocations(['partner'])
    }]
};
