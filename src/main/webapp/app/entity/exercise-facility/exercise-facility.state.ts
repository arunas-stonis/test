import { ExerciseFacilityAppComponent } from './exercise-facility-app.component';
import { JhiLanguageService } from '../../shared';

export const exerciseFacilityState = {
    name: 'exercise-facility',
    parent: 'entity',
    url: '/exercise-facility',
    data: {
        authorities: ['ROLE_USER', 'ROLE_ADMIN'],
        pageTitle: 'global.menu.entity.exercise-facility'
    },
    views: {
        'content@': {
            component: ExerciseFacilityAppComponent
        }
    },
    resolve: [{
        token: 'translate',
        deps: [JhiLanguageService],
        resolveFn: (languageService) => languageService.setLocations(['exercise-facility'])
    }]
};
