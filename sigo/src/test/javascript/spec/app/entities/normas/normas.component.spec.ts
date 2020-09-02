import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SigoTestModule } from '../../../test.module';
import { NormasComponent } from 'app/entities/normas/normas.component';
import { NormasService } from 'app/entities/normas/normas.service';
import { Normas } from 'app/shared/model/normas.model';

describe('Component Tests', () => {
  describe('Normas Management Component', () => {
    let comp: NormasComponent;
    let fixture: ComponentFixture<NormasComponent>;
    let service: NormasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [NormasComponent],
      })
        .overrideTemplate(NormasComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NormasComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NormasService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Normas(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.normas && comp.normas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
