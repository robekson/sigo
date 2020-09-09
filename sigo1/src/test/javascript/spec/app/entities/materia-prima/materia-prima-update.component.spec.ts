import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigoTestModule } from '../../../test.module';
import { MateriaPrimaUpdateComponent } from 'app/entities/materia-prima/materia-prima-update.component';
import { MateriaPrimaService } from 'app/entities/materia-prima/materia-prima.service';
import { MateriaPrima } from 'app/shared/model/materia-prima.model';

describe('Component Tests', () => {
  describe('MateriaPrima Management Update Component', () => {
    let comp: MateriaPrimaUpdateComponent;
    let fixture: ComponentFixture<MateriaPrimaUpdateComponent>;
    let service: MateriaPrimaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [MateriaPrimaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MateriaPrimaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MateriaPrimaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MateriaPrimaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MateriaPrima(123);
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
        const entity = new MateriaPrima();
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
