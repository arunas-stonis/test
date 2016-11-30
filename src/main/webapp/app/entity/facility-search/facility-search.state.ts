import { FacilitySearchComponent } from './facility-search.component';
import { JhiLanguageService } from '../../shared';

export const facilitySearchState = {
    name: 'facility-search',
    parent: 'entity',
    url: '/facility-search',
    data: {
        authorities: ['ROLE_USER', 'ROLE_ADMIN'],
        pageTitle: 'global.menu.entity.facility-search'
    },
    views: {
        'content@': {
            component: FacilitySearchComponent
        }
    },
    resolve: [{
        token: 'translate',
        deps: [JhiLanguageService],
        resolveFn: (languageService) => languageService.setLocations(['facility-search'])
    }]
};
