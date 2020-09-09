import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigoTestModule } from '../../../test.module';
import { ForneceUpdateComponent } from 'app/entities/fornece/fornece-update.component';
import { ForneceService } from 'app/entities/fornece/fornece.service';
import { Fornece } from 'app/shared/model/fornece.model';

describe('Component Tests', () => {
  describe('Fornece Management Update Component', () => {
    let comp: ForneceUpdateComponent;
    let fixture: ComponentFixture<ForneceUpdateComponent>;
    let service: ForneceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [ForneceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ForneceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ForneceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ForneceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Fornece(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Fornece();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
