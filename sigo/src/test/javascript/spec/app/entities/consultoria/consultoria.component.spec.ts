import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SigoTestModule } from '../../../test.module';
import { ConsultoriaComponent } from 'app/entities/consultoria/consultoria.component';
import { ConsultoriaService } from 'app/entities/consultoria/consultoria.service';
import { Consultoria } from 'app/shared/model/consultoria.model';

describe('Component Tests', () => {
  describe('Consultoria Management Component', () => {
    let comp: ConsultoriaComponent;
    let fixture: ComponentFixture<ConsultoriaComponent>;
    let service: ConsultoriaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [ConsultoriaComponent],
      })
        .overrideTemplate(ConsultoriaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConsultoriaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConsultoriaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Consultoria(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.consultorias && comp.consultorias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
