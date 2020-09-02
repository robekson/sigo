import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigoTestModule } from '../../../test.module';
import { NormasUpdateComponent } from 'app/entities/normas/normas-update.component';
import { NormasService } from 'app/entities/normas/normas.service';
import { Normas } from 'app/shared/model/normas.model';

describe('Component Tests', () => {
  describe('Normas Management Update Component', () => {
    let comp: NormasUpdateComponent;
    let fixture: ComponentFixture<NormasUpdateComponent>;
    let service: NormasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [NormasUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NormasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NormasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NormasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Normas(123);
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
        const entity = new Normas();
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
