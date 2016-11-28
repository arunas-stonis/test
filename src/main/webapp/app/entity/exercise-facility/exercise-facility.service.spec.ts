/* tslint:disable:no-unused-variable */
///<reference path='../../../../../../node_modules/@types/jasmine/index.d.ts' />
import { TestBed, async, inject } from '@angular/core/testing';
import { ExerciseFacilityService } from './exercise-facility.service';
import { ExerciseFacility } from './exercise-facility';

describe('ExerciseFacilityService', () => {
  beforeEach(() => {
    TestBed .configureTestingModule({
      providers: [ExerciseFacilityService]
    });
  });

  // it('should ...', inject([ExerciseFacilityService], (service: ExerciseFacilityService) => {
  //   expect(service).toBeTruthy();
  // }));

  // beforeEachProviders(() => [ExerciseFacility]);

  describe('#getAllFacilities()', () => {

    it('should return an empty array by default', inject([ExerciseFacilityService], (service: ExerciseFacilityService) => {
      expect(service.getAllFacilities()).toEqual([]);
    }));

    it('should return all todos', inject([ExerciseFacilityService], (service: ExerciseFacilityService) => {
      let facility1 = new ExerciseFacility({name: 'Hello 1', categoryId: 1, address: 'xyz', description: 'abc' });
      let facility2 = new ExerciseFacility({name: 'Hello 2', categoryId: 1, address: 'xyz', description: 'abc'});
      service.addFacility(facility1);
      service.addFacility(facility2);
      expect(service.getAllFacilities()).toEqual([facility1, facility2]);
    }));

  });

  // describe('#save(todo)', () => {
  //
  //   it('should automatically assign an incrementing id', inject([TodoService], (service: TodoService) => {
  //     let todo1 = new Todo({title: 'Hello 1', complete: false});
  //     let todo2 = new Todo({title: 'Hello 2', complete: true});
  //     service.addTodo(todo1);
  //     service.addTodo(todo2);
  //     expect(service.getTodoById(1)).toEqual(todo1);
  //     expect(service.getTodoById(2)).toEqual(todo2);
  //   }));
  //
  // });
  //
  // describe('#deleteTodoById(id)', () => {
  //
  //   it('should remove todo with the corresponding id', inject([TodoService], (service: TodoService) => {
  //     let todo1 = new Todo({title: 'Hello 1', complete: false});
  //     let todo2 = new Todo({title: 'Hello 2', complete: true});
  //     service.addTodo(todo1);
  //     service.addTodo(todo2);
  //     expect(service.getAllTodos()).toEqual([todo1, todo2]);
  //     service.deleteTodoById(1);
  //     expect(service.getAllTodos()).toEqual([todo2]);
  //     service.deleteTodoById(2);
  //     expect(service.getAllTodos()).toEqual([]);
  //   }));
  //
  //   it('should not removing anything if todo with corresponding id is not found', inject([TodoService], (service: TodoService) => {
  //     let todo1 = new Todo({title: 'Hello 1', complete: false});
  //     let todo2 = new Todo({title: 'Hello 2', complete: true});
  //     service.addTodo(todo1);
  //     service.addTodo(todo2);
  //     expect(service.getAllTodos()).toEqual([todo1, todo2]);
  //     service.deleteTodoById(3);
  //     expect(service.getAllTodos()).toEqual([todo1, todo2]);
  //   }));
  //
  // });
  //
  // describe('#updateTodoById(id, values)', () => {
  //
  //   it('should return todo with the corresponding id and updated data', inject([TodoService], (service: TodoService) => {
  //     let todo = new Todo({title: 'Hello 1', complete: false});
  //     service.addTodo(todo);
  //     let updatedTodo = service.updateTodoById(1, {
  //       title: 'new title'
  //     });
  //     expect(updatedTodo.title).toEqual('new title');
  //   }));
  //
  //   it('should return null if todo is not found', inject([TodoService], (service: TodoService) => {
  //     let todo = new Todo({title: 'Hello 1', complete: false});
  //     service.addTodo(todo);
  //     let updatedTodo = service.updateTodoById(2, {
  //       title: 'new title'
  //     });
  //     expect(updatedTodo).toEqual(null);
  //   }));
  //
  // });
  //
  // describe('#toggleTodoComplete(todo)', () => {
  //
  //   it('should return the updated todo with inverse complete status', inject([TodoService], (service: TodoService) => {
  //     let todo = new Todo({title: 'Hello 1', complete: false});
  //     service.addTodo(todo);
  //     let updatedTodo = service.toggleTodoComplete(todo);
  //     expect(updatedTodo.complete).toEqual(true);
  //     service.toggleTodoComplete(todo);
  //     expect(updatedTodo.complete).toEqual(false);
  //   }));
  //
  // });

});
