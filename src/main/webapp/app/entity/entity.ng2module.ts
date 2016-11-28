import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { UIRouterModule } from 'ui-router-ng2';

import { MyappSharedModule } from '../shared';

import {
    ExerciseFacilityAppComponent,
    ExerciseFacilityService,
    entityState,
    exerciseFacilityState
} from './';

let ENTITY_STATES = [
    entityState,
    exerciseFacilityState
];

@NgModule({
    imports: [
        MyappSharedModule,
        UIRouterModule.forChild({ states: ENTITY_STATES })
    ],
    declarations: [
        ExerciseFacilityAppComponent
    ],
    providers: [
        ExerciseFacilityService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyappEntitytModule {}
