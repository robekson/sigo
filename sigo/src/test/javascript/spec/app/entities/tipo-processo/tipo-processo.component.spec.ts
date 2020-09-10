import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SigoTestModule } from '../../../test.module';
import { TipoProcessoComponent } from 'app/entities/tipo-processo/tipo-processo.component';
import { TipoProcessoService } from 'app/entities/tipo-processo/tipo-processo.service';
import { TipoProcesso } from 'app/shared/model/tipo-processo.model';

describe('Component Tests', () => {
  describe('TipoProcesso Management Component', () => {
    let comp: TipoProcessoComponent;
    let fixture: ComponentFixture<TipoProcessoComponent>;
    let service: TipoProcessoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [TipoProcessoComponent],
      })
        .overrideTemplate(TipoProcessoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoProcessoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoProcessoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoProcesso(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoProcessos && comp.tipoProcessos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
