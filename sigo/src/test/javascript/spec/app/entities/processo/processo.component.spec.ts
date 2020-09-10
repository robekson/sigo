import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SigoTestModule } from '../../../test.module';
import { ProcessoComponent } from 'app/entities/processo/processo.component';
import { ProcessoService } from 'app/entities/processo/processo.service';
import { Processo } from 'app/shared/model/processo.model';

describe('Component Tests', () => {
  describe('Processo Management Component', () => {
    let comp: ProcessoComponent;
    let fixture: ComponentFixture<ProcessoComponent>;
    let service: ProcessoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [ProcessoComponent],
      })
        .overrideTemplate(ProcessoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProcessoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProcessoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Processo(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.processos && comp.processos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
