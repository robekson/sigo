import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SigoTestModule } from '../../../test.module';
import { TipoProcessoUpdateComponent } from 'app/entities/tipo-processo/tipo-processo-update.component';
import { TipoProcessoService } from 'app/entities/tipo-processo/tipo-processo.service';
import { TipoProcesso } from 'app/shared/model/tipo-processo.model';

describe('Component Tests', () => {
  describe('TipoProcesso Management Update Component', () => {
    let comp: TipoProcessoUpdateComponent;
    let fixture: ComponentFixture<TipoProcessoUpdateComponent>;
    let service: TipoProcessoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [TipoProcessoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TipoProcessoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoProcessoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoProcessoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoProcesso(123);
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
        const entity = new TipoProcesso();
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
